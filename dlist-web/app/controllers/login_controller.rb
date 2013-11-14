class LoginController < ApplicationController
  def index
    unless @current_user
      @dropbox_authorize_url = get_web_auth.start()
    end
  end

  def logout
    clear_dropbox_auth
    redirect_to :action => :index
  end

  def authenticate
    begin
      access_token, user_id, url_state = get_web_auth.finish(params)
      session[:access_token] = access_token
      session[:uid] = user_id

      client = DropboxClient.new(session[:access_token])
      User.where(:uid => session[:uid], :access_token => session[:access_token]).first_or_create!(:display_name => client.account_info["display_name"], :email => client.account_info["email"])

      begin
        csv_file = client.get_file_and_metadata('todo_items.csv')
      rescue DropboxAuthError => e
        clear_dropbox_auth
        logger.info "Dropbox auth error: #{e}"
        render :text => "Dropbox auth error"
        return
      rescue DropboxError => e
        client.put_file('todo_items.csv', TodoItem.headers.join(','))
        csv_file = client.get_file_and_metadata('todo_items.csv')
      end
      CsvFile.where(:uid => session[:uid]).first_or_create!(:content => csv_file[0], :revision => csv_file[1]["revision"])

      redirect_to :controller => 'todo_items', :action => 'index'
    rescue DropboxOAuth2Flow::BadRequestError => e
      render :text => "Error in OAuth 2 flow: Bad request: #{e}"
    rescue DropboxOAuth2Flow::BadStateError => e
      logger.info("Error in OAuth 2 flow: No CSRF token in session: #{e}")
      redirect_to(:action => 'auth_start')
    rescue DropboxOAuth2Flow::CsrfError => e
      logger.info("Error in OAuth 2 flow: CSRF mismatch: #{e}")
      render :text => "CSRF error"
    rescue DropboxOAuth2Flow::NotApprovedError => e
      render :text => "Not approved?  Why not, bro?"
    rescue DropboxOAuth2Flow::ProviderError => e
      logger.info "Error in OAuth 2 flow: Error redirect from Dropbox: #{e}"
      render :text => "Strange error."
    rescue DropboxError => e
      logger.info "Error getting OAuth 2 access token: #{e}"
      render :text => "Error communicating with Dropbox servers."
    end
  end

  private
  def get_web_auth
    redirect_uri = url_for(:action => 'authenticate')
    DropboxOAuth2Flow.new(ENV['DROPBOX_APP_KEY'], ENV['DROPBOX_APP_SECRET'], redirect_uri, session, :dropbox_auth_csrf_token)
  end
end
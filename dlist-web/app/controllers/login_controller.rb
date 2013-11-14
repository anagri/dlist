class LoginController < ApplicationController
  def index
    @dropbox_authorize_url = get_web_auth().start()
  end

  def authenticate
    begin
      access_token, user_id, url_state = get_web_auth.finish(params)
      session[:access_token] = access_token
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
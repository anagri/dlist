class ApplicationController < ActionController::Base
  protect_from_forgery
  before_filter :current_user

  protected

  def current_user
    if session[:uid] && session[:access_token]
      @current_user ||= User.where(:uid => session[:uid], :access_token => session[:access_token]).first
    end
  end

  def authenticate_or_redirect
    unless current_user
      flash[:warning] = 'You are not logged in. Kindly log in to Dropbox.'
      redirect_to :controller => :login, :action => :index
    end
  end

  def get_dropbox_client
    if current_user
      begin
        DropboxClient.new(current_user.access_token)
      rescue
        # Maybe something's wrong with the access token?
        clear_dropbox_auth
        raise
      end
    else
      flash[:warning] = 'You are not logged in. Kindly log in to Dropbox.'
      redirect_to(:controller => 'login', :action => 'index') and return
    end
  end

  def clear_dropbox_auth
    User.where(:uid => session[:uid], :access_token => session[:access_token]).delete_all
    session.delete(:access_token)
    session.delete(:uid)
    reset_session
  end
end

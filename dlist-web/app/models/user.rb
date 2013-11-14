class User < ActiveRecord::Base
  attr_accessible :uid, :display_name, :email, :access_token
end

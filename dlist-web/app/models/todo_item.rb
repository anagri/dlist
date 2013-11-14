class TodoItem < ActiveRecord::Base
  attr_accessible :attachment, :geo_reminder, :owner, :priority, :reminder, :status, :title
end

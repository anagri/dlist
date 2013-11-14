require 'csv'

class CsvFile < ActiveRecord::Base
  attr_accessible :content, :uid, :revision

  def current_user=(current_user)
    @current_user = current_user
  end

  def refresh
    updated_remotely?
    self
  end

  def updated_remotely?
    client = DropboxClient.new(@current_user.access_token)
    csv_file = client.get_file_and_metadata('todo_items.csv')
    orig_csv_file = CsvFile.find(@current_user.uid)

    if orig_csv_file.revision != csv_file[1]["revision"].to_i
      orig_csv_file.update_attributes!(:content => csv_file[0], :revision => csv_file[1]["revision"])
      return true
    end

    false
  end

  def todo_items
    TodoItems.new(@current_user, content)
  end

  def insert(todo_item)
    todo_items.insert(todo_item)
  end

  def update_item(current_user, params)

  end

  def self.csv_for_user(current_user)
    csv_file = CsvFile.find(current_user.uid)
    csv_file.current_user = current_user
    csv_file
  end
end
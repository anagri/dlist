require 'csv'

class TodoItems < Array
  def initialize(current_user, content)
    @current_user = current_user
    populate(content)
  end

  def persist
    this = self
    final_val = CSV.generate do |csv|
      csv << TodoItem.headers
      this.each do |row|
        csv << row.to_a
      end
    end

    client = DropboxClient.new(@current_user.access_token)
    response = client.put_file('todo_items.csv', final_val, true)
    CsvFile.find(@current_user.uid).update_attributes!(:content => final_val, :revision => response["revision"])
  end

  def destroy(id)
    self.delete_at(id)
    persist
  end

  def insert(todo_item)
    self.push(todo_item)
    persist
  end

  private
  def populate(content)
    CSV.parse(content, :headers => true) do |row|
      self << TodoItem.new(row)
    end
  end
end
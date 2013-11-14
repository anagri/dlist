class TodoItem
  #attr_accessible :content, :uid, :revision
  include ActiveModel::Validations
  include ActiveModel::Conversion
  extend ActiveModel::Naming

  attr_accessor :attachment, :geo_reminder, :owner, :priority, :reminder, :status, :title, :created_at

  def initialize(params = {})
    @title = params["title"]
    @status = params["status"]
    @reminder = params["reminder"]
    @priority = params["priority"]
    @owner = params["owner"]
    @geo_reminder = params["geo_reminder"]
    @created_at = DateTime.now.strftime('%d/%m/%y %H:%M')
  end

  def persisted?
    false
  end

  def save(current_user)
    csv = TodoItem.csv_for_user(current_user).refresh
    csv.insert(self)
  end

  def update_attributes(current_user, params)
    csv = TodoItem.csv_for_user(current_user)
    if csv.updated_remotely?
      false
    else
      csv.update(params[:id], params)
      true
    end
  end

  def destroy(current_user)
    csv = TodoItem.csv_for_user(current_user)
    if csv.updated_remotely?
      return false
    else
      csv.destroy(params[:id])
    end
  end

  def to_a
    [@title, @status, @owner, @priority, @reminder, @geo_reminder, '', @created_at]
  end

  def self.all(current_user)
    csv_for_user(current_user).refresh.todo_items
  end

  def self.headers
    ['title', 'status', 'assigned to', 'priority', 'remind at', 'geo remind at', 'attachments', 'created by', 'created at']
  end

  def self.priorities
    [['High', 'High'], ['Medium', 'Medium'], ['Low', 'Low']]
  end

  def self.statuses
    [['Pending', 'Pending'], ['In Progress', 'In Progress'], ['Complete', 'Complete']]
  end

  def self.geo_reminders
    [['None', ''], ['Home', 'Home'], ['Office', 'Office'], ['Market', 'Market']]
  end

  private
  def self.csv_for_user(current_user)
    CsvFile.csv_for_user(current_user)
  end
end

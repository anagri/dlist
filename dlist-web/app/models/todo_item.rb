class TodoItem
  #attr_accessible :content, :uid, :revision
  include ActiveModel::Validations
  include ActiveModel::Conversion
  extend ActiveModel::Naming

  attr_accessor :attachment, :geo_remind_at, :assigned_to, :priority, :remind_at, :status, :title, :created_at

  def initialize(params = {})
    normalized_hash = {}
    params.each do |k, v|
      normalized_hash[k.gsub(' ', '_')] = v
    end

    @title = normalized_hash["title"]
    @status = normalized_hash["status"]
    @assigned_to = normalized_hash["assigned_to"]
    @priority = normalized_hash["priority"]
    @remind_at = normalized_hash["remind_at"]
    @geo_remind_at = normalized_hash["geo_remind_at"]
    @created_at = normalized_hash["created_at"].present? ? normalized_hash["created_at"] : DateTime.now.strftime('%d/%m/%y %H:%M')
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
    [@title, @status, @assigned_to, @priority, @remind_at, @geo_remind_at, '', @created_at]
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

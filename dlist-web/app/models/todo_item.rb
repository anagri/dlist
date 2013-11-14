class TodoItem < ActiveRecord::Base
  attr_accessible :attachment, :geo_reminder, :owner, :priority, :reminder, :status, :title

  def self.before_create
    name =  upload['datafile'].original_filename
    directory = "public/data"
    # create the file path
    path = File.join(directory, name)
    # write the file
    File.open(path, "wb") { |f| f.write(upload['datafile'].read) }
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
end

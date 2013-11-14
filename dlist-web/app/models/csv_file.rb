class CsvFile < ActiveRecord::Base
  attr_accessible :content, :uid, :revision
end

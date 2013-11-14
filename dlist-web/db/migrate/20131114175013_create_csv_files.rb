class CreateCsvFiles < ActiveRecord::Migration
  def change
    create_table :csv_files, :primary_key => :uid do |t|
      t.text :content
      t.integer :revision
      t.timestamps
    end
  end
end

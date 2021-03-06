class CreateTodoItems < ActiveRecord::Migration
  def change
    create_table :todo_items do |t|
      t.string :title
      t.string :owner
      t.string :priority
      t.string :status
      t.datetime :remind_at
      t.string :geo_reminder
      t.string :attachment

      t.timestamps
    end
  end
end

class CreateUsers < ActiveRecord::Migration
  def change
    create_table :users, :id => false do |t|
      t.integer :uid
      t.string :display_name
      t.string :email
      t.string :access_token

      t.timestamps
    end

    add_index :users, [:uid, :access_token], :unique => true
  end
end

package com.liyh.aidlclient;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class ObservableUser extends BaseObservable {
   private String firstName;
   private String lastName;

   @Bindable
   public String getFirstName() {
       return firstName;
   }

 // 注解才会自动在build目录BR类中生成entry, 要求方法名必须以get开头
   @Bindable
   public String getLastName() {
       return lastName;
   }

   public void setFirstName(String firstName) {
       this.firstName = firstName;
       notifyPropertyChanged(BR.firstName);
   }

   public void setLastName(String lastName) {
       this.lastName = lastName;
       notifyPropertyChanged(BR.lastName); // 需要手动刷新
   }
}

package com.oreilly.exception.example;
public class BaseException extends Exception {
  public Object[] getArgs() {
    return this.args;
  }
  public void setArgs(Object[] newArgs) {
    this.args = newArgs;
  }
  public BaseException(String newMsgKey, 
                       Object[] newArgs) {
    messageKey = newMsgKey;
    args = newArgs;
  }
  public String getMessageKey() {
    return messageKey;
  }
  public void setMessageKey(String newKey) {
    this.messageKey = newKey;
  }
  
  // The key to the bundle message
  protected String messageKey = null;
  
  // Array of arguments to use with the bundle message
  Object[] args = null;
}

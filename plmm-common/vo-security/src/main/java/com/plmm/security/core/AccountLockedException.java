package com.plmm.security.core;

public class AccountLockedException extends AuthenticationException{
  public AccountLockedException(String msg)
  {
    super(msg);
  }

  public AccountLockedException(String msg, Throwable t)
  {
    super(msg, t);
  }
}

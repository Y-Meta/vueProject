package com.plmm.security.core.userdetails;

public abstract interface UserDetailsService{
  public abstract UserDetails loadUserByUsername(String paramString);

  public abstract void loginFailure(String paramString);
}
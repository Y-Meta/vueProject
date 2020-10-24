package com.plmm.security.core.context;

import java.io.Serializable;

import com.plmm.security.core.Authentication;

public abstract interface SecurityContext extends Serializable{
  public abstract Authentication getAuthentication();

  public abstract void setAuthentication(Authentication paramAuthentication);
}
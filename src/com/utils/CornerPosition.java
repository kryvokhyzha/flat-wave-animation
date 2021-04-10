package com.utils;

public enum CornerPosition {
  TOPLEFT("top-left"),
  TOPRIGHT("top-right"),
  BOTTOMLEFT("bottom-left"),
  BOTTOMRIGHT("bottom-right");

  private final String position;

  CornerPosition(String position) {
    this.position = position;
  }

  public String getPosition() {
    return position;
  }
}

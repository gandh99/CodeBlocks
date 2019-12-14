package com.gandh99.codeblocks.projectPage.members;

public enum Rank {
  ADMIN("ADMIN"),
  MEMBER("MEMBER");

  private String rankName;

  Rank(String rankName) {
    this.rankName = rankName;
  }

  public String getRankName() {
    return rankName;
  }
}

package com.roboxue.buildplan;

/**
 * @author robert.xue
 * @since 226
 */
public class ProjectDigest {
  private String groupId;
  private String artifactId;
  private String version;
  private String name;

  public String getType() {
    return "project";
  }


  public String getGroupId() {
    return groupId;
  }

  public String getArtifactId() {
    return artifactId;
  }

  public String getVersion() {
    return version;
  }

  public String getName() {
    return name;
  }

  public static final class ProjectDigestBuilder {

    private String groupId;
    private String artifactId;
    private String version;
    private String name;

    private ProjectDigestBuilder() {
    }

    public static ProjectDigestBuilder aProjectDigest() {
      return new ProjectDigestBuilder();
    }

    public ProjectDigestBuilder withGroupId(String groupId) {
      this.groupId = groupId;
      return this;
    }

    public ProjectDigestBuilder withArtifactId(String artifactId) {
      this.artifactId = artifactId;
      return this;
    }

    public ProjectDigestBuilder withVersion(String version) {
      this.version = version;
      return this;
    }

    public ProjectDigestBuilder withName(String name) {
      this.name = name;
      return this;
    }

    public ProjectDigest build() {
      ProjectDigest projectDigest = new ProjectDigest();
      projectDigest.name = this.name;
      projectDigest.groupId = this.groupId;
      projectDigest.artifactId = this.artifactId;
      projectDigest.version = this.version;
      return projectDigest;
    }
  }
}

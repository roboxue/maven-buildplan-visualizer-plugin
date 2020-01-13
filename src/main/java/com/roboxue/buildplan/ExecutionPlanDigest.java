package com.roboxue.buildplan;

/**
 * @author robert.xue
 * @since 226
 */
public class ExecutionPlanDigest {

  private String executionId;
  private String phase;
  private String pluginGroupId;
  private String pluginArtifactId;
  private String pluginVersion;
  private String goal;
  private String source;
  private String configXml;

  public String getType() {
    return "execution";
  }

  public String getExecutionId() {
    return executionId;
  }

  public String getPhase() {
    return phase;
  }

  public String getPluginGroupId() {
    return pluginGroupId;
  }

  public String getPluginArtifactId() {
    return pluginArtifactId;
  }

  public String getPluginVersion() {
    return pluginVersion;
  }

  public String getGoal() {
    return goal;
  }

  public String getSource() {
    return source;
  }

  public String getConfigXml() {
    return configXml;
  }

  public static final class ExecutionPlanDigestBuilder {

    private String executionId;
    private String phase;
    private String pluginGroupId;
    private String pluginArtifactId;
    private String pluginVersion;
    private String goal;
    private String source;
    private String configXml;

    private ExecutionPlanDigestBuilder() {
    }

    public static ExecutionPlanDigestBuilder anExecutionPlanDigest() {
      return new ExecutionPlanDigestBuilder();
    }

    public ExecutionPlanDigestBuilder withExecutionId(String executionId) {
      this.executionId = executionId;
      return this;
    }

    public ExecutionPlanDigestBuilder withPhase(String phase) {
      this.phase = phase;
      return this;
    }

    public ExecutionPlanDigestBuilder withPluginGroupId(String pluginGroupId) {
      this.pluginGroupId = pluginGroupId;
      return this;
    }

    public ExecutionPlanDigestBuilder withPluginArtifactId(String pluginArtifactId) {
      this.pluginArtifactId = pluginArtifactId;
      return this;
    }

    public ExecutionPlanDigestBuilder withPluginVersion(String pluginVersion) {
      this.pluginVersion = pluginVersion;
      return this;
    }

    public ExecutionPlanDigestBuilder withGoal(String goal) {
      this.goal = goal;
      return this;
    }

    public ExecutionPlanDigestBuilder withSource(String source) {
      this.source = source;
      return this;
    }

    public ExecutionPlanDigestBuilder withConfigXml(String configXml) {
      this.configXml = configXml;
      return this;
    }

    public ExecutionPlanDigest build() {
      ExecutionPlanDigest executionPlanDigest = new ExecutionPlanDigest();
      executionPlanDigest.source = this.source;
      executionPlanDigest.executionId = this.executionId;
      executionPlanDigest.phase = this.phase;
      executionPlanDigest.pluginVersion = this.pluginVersion;
      executionPlanDigest.configXml = this.configXml;
      executionPlanDigest.pluginGroupId = this.pluginGroupId;
      executionPlanDigest.pluginArtifactId = this.pluginArtifactId;
      executionPlanDigest.goal = this.goal;
      return executionPlanDigest;
    }
  }
}

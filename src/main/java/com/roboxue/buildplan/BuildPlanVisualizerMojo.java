package com.roboxue.buildplan;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.roboxue.buildplan.ExecutionPlanDigest.ExecutionPlanDigestBuilder;
import com.roboxue.buildplan.ProjectDigest.ProjectDigestBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.apache.maven.execution.MavenSession;
import org.apache.maven.lifecycle.LifecycleExecutor;
import org.apache.maven.lifecycle.MavenExecutionPlan;
import org.apache.maven.lifecycle.internal.ExecutionPlanItem;
import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @author robert.xue
 * @since 226
 */
@Mojo(name = "buildplan-visualizer")
public class BuildPlanVisualizerMojo extends AbstractMojo {

  @Parameter(defaultValue="${session}", readonly = true)
  private MavenSession session;

  @Parameter(defaultValue = "${project}", readonly = true)
  private MavenProject project;

  @Component
  private LifecycleExecutor lifecycleExecutor;

  @Parameter(property = "buildplan.tasks", defaultValue = "deploy")
  private String[] tasks;

  @Parameter(property = "buildplan.output", defaultValue = "${maven.multiModuleProjectDirectory}/target/buildplan")
  private String output;

  private MavenExecutionPlan calculateExecutionPlan() throws MojoFailureException {
    try {
      return lifecycleExecutor.calculateExecutionPlan(session, tasks);
    } catch (Exception e) {
      throw new MojoFailureException(String.format("Cannot calculate Maven execution plan, caused by: %s", e.getMessage()));
    }
  }

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    MavenExecutionPlan plan;
    try {
      plan = lifecycleExecutor.calculateExecutionPlan(session, tasks);
    } catch (Exception e) {
      throw new MojoFailureException(String.format("Cannot calculate Maven execution plan, caused by: %s", e.getMessage()));
    }
    ObjectMapper mapper = new ObjectMapper();
    Path path = Paths.get(output, "buildplan.jsonl");
    path.toFile().getParentFile().mkdirs();
    try (FileWriter fw = new FileWriter(path.toFile(), true)) {
      ProjectDigest projectDigest = ProjectDigestBuilder.aProjectDigest()
          .withName(project.getName())
          .withArtifactId(project.getArtifactId())
          .withGroupId(project.getGroupId())
          .withVersion(project.getVersion())
          .build();
      fw.write(mapper.writeValueAsString(projectDigest));
      fw.append(System.lineSeparator());
      for (ExecutionPlanItem p : plan) {
        String phase = p.getLifecyclePhase();
        Plugin plugin = p.getPlugin();
        ExecutionPlanDigest digest = ExecutionPlanDigestBuilder.anExecutionPlanDigest()
            .withExecutionId(p.getMojoExecution().getExecutionId())
            .withPhase(phase)
            .withPluginArtifactId(plugin.getArtifactId())
            .withPluginGroupId(plugin.getGroupId())
            .withPluginVersion(plugin.getVersion())
            .withPluginVersion(plugin.getVersion())
            .withSource(p.getMojoExecution().getSource().name())
            .withGoal(p.getMojoExecution().getGoal())
            .withConfigXml(p.getMojoExecution().getConfiguration().toString())
            .build();
        fw.write(mapper.writeValueAsString(digest));
        fw.append(System.lineSeparator());
      }
      fw.flush();
    } catch (IOException e) {
      throw new MojoFailureException("Failed to save json digest output", e);
    }
    String[] dist = new String[] {
        "css/chunk-vendors.50d096d6.css",
        "js/app.d2ec43b8.js",
        "js/app.d2ec43b8.js.map",
        "js/chunk-vendors.2cf31ac9.js",
        "js/chunk-vendors.2cf31ac9.js.map",
        "favicon.ico",
        "index.html"
    };
    ClassLoader cl = getClass().getClassLoader();
    for (String file: dist) {
      try {
        Path dest = Paths.get(output, file);
        Files.createDirectories(dest.getParent());
        Files.copy(Objects.requireNonNull(cl.getResourceAsStream(file)), dest, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException e) {
        throw new MojoFailureException("Failed to copy server file", e);
      }
    }
    getLog().info(String.format("Buildplan digest written to %s", path.toString()));
  }
}

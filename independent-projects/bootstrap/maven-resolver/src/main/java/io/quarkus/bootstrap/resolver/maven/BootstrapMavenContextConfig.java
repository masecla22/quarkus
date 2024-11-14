package io.quarkus.bootstrap.resolver.maven;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.apache.maven.model.Model;
import org.apache.maven.settings.crypto.SettingsDecrypter;
import org.eclipse.aether.RepositorySystem;
import org.eclipse.aether.RepositorySystemSession;
import org.eclipse.aether.impl.RemoteRepositoryManager;
import org.eclipse.aether.repository.RemoteRepository;

import io.quarkus.bootstrap.resolver.maven.options.BootstrapMavenOptions;
import io.quarkus.bootstrap.resolver.maven.workspace.LocalProject;

public class BootstrapMavenContextConfig<T extends BootstrapMavenContextConfig<?>> {

    protected String localRepo;
    protected String[] localRepoTail;
    protected Boolean localRepoTailIgnoreAvailability;
    protected Boolean offline;
    protected LocalProject currentProject;
    protected boolean workspaceDiscovery = true;
    protected RepositorySystem repoSystem;
    protected RepositorySystemSession repoSession;
    protected List<RemoteRepository> remoteRepos;
    protected List<RemoteRepository> remotePluginRepos;
    protected RemoteRepositoryManager remoteRepoManager;
    protected SettingsDecrypter settingsDecrypter;
    protected String alternatePomName;
    protected File userSettings;
    protected boolean artifactTransferLogging = true;
    protected BootstrapMavenOptions cliOptions;
    protected Path rootProjectDir;
    protected boolean preferPomsFromWorkspace;
    protected Boolean effectiveModelBuilder;
    protected Boolean wsModuleParentHierarchy;
    protected Function<Path, Model> modelProvider;
    protected List<String> excludeSisuBeanPackages;
    protected List<String> includeSisuBeanPackages;
    protected Boolean warnOnFailedWorkspaceModules;

    public T excludeSisuBeanPackage(String packageName) {
        if (excludeSisuBeanPackages == null) {
            excludeSisuBeanPackages = new ArrayList<>();
        }
        excludeSisuBeanPackages.add(packageName);
        return (T) this;
    }

    protected List<String> getExcludeSisuBeanPackages() {
        if (excludeSisuBeanPackages == null) {
            return List.of("org.apache.maven.shared.release",
                    "org.apache.maven.toolchain",
                    "org.apache.maven.lifecycle",
                    "org.apache.maven.execution",
                    "org.apache.maven.plugin");
        }
        return excludeSisuBeanPackages;
    }

    public T includeSisuBeanPackage(String packageName) {
        if (includeSisuBeanPackages == null) {
            includeSisuBeanPackages = new ArrayList<>();
        }
        includeSisuBeanPackages.add(packageName);
        return (T) this;
    }

    protected List<String> getIncludeSisuBeanPackages() {
        if (includeSisuBeanPackages == null) {
            return List.of("io.smallrye.beanbag",
                    "org.eclipse.aether",
                    "org.sonatype.plexus.components",
                    "org.apache.maven");
        }
        return includeSisuBeanPackages;
    }

    /**
     * Local repository location
     *
     * @param localRepo local repository location
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setLocalRepository(String localRepo) {
        this.localRepo = localRepo;
        return (T) this;
    }

    /**
     * Local repository tail locations (comma-separated)
     *
     * @param localRepoTail local repository tail locations (comma-separated)
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setLocalRepositoryTail(String... localRepoTail) {
        this.localRepoTail = localRepoTail;
        return (T) this;
    }

    /**
     * Wheter to ignore availability on local repository tail (default: true)
     *
     * @param localRepoTailIgnoreAvailability whether to ignore availability on local repository tail
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setLocalRepositoryTailIgnoreAvailability(boolean localRepoTailIgnoreAvailability) {
        this.localRepoTailIgnoreAvailability = localRepoTailIgnoreAvailability;
        return (T) this;
    }

    /**
     * Whether to operate offline
     *
     * @param offline whether to operate offline
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setOffline(boolean offline) {
        this.offline = offline;
        return (T) this;
    }

    /**
     * Workspace in the context of which this configuration is being initialized
     *
     * @param currentProject current project
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setCurrentProject(LocalProject currentProject) {
        this.currentProject = currentProject;
        return (T) this;
    }

    /**
     * Enables or disables workspace discovery.
     * By default, workspace discovery is enabled, meaning that when
     * the resolver is created in the context of a Maven project, the Maven's project
     * POM configuration will be picked up by the resolver and all the local projects
     * belonging to the workspace will be resolved at their original locations instead of
     * the actually artifacts installed in the repository.
     *
     * @param workspaceDiscovery enables or disables workspace discovery
     * @return this instance of the builder
     */
    @SuppressWarnings("unchecked")
    public T setWorkspaceDiscovery(boolean workspaceDiscovery) {
        this.workspaceDiscovery = workspaceDiscovery;
        return (T) this;
    }

    /**
     * RepositorySystem that should be used by the resolver
     *
     * @param repoSystem
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setRepositorySystem(RepositorySystem repoSystem) {
        this.repoSystem = repoSystem;
        return (T) this;
    }

    /**
     * RepositorySystemSession that should be used by the resolver
     *
     * @param repoSession repository session
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setRepositorySystemSession(RepositorySystemSession repoSession) {
        this.repoSession = repoSession;
        return (T) this;
    }

    /**
     * Remote repositories that should be used by the resolver
     *
     * @param remoteRepos remote repositories
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setRemoteRepositories(List<RemoteRepository> remoteRepos) {
        this.remoteRepos = remoteRepos;
        return (T) this;
    }

    /**
     * Remote plugin repositories that should be used by the resolver
     *
     * @param remotePluginRepos remote plugin repositories
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setRemotePluginRepositories(List<RemoteRepository> remotePluginRepos) {
        this.remotePluginRepos = remotePluginRepos;
        return (T) this;
    }

    /**
     * Remote repository manager
     *
     * @param remoteRepoManager
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setRemoteRepositoryManager(RemoteRepositoryManager remoteRepoManager) {
        this.remoteRepoManager = remoteRepoManager;
        return (T) this;
    }

    /**
     * Settings decryptor
     *
     * @param settingsDecrypter settings decrypter
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setSettingsDecrypter(SettingsDecrypter settingsDecrypter) {
        this.settingsDecrypter = settingsDecrypter;
        return (T) this;
    }

    /**
     * The meaning of this option is equivalent to alternative POM in Maven,
     * which can be specified with command line argument '-f'.
     *
     * @param currentProject
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setCurrentProject(String currentProject) {
        this.alternatePomName = currentProject;
        return (T) this;
    }

    /**
     * User Maven settings file location
     *
     * @param userSettings
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setUserSettings(File userSettings) {
        this.userSettings = userSettings;
        return (T) this;
    }

    /**
     * Whether to enable progress logging of artifact transfers.
     * The default value is true.
     *
     * @param artifactTransferLogging
     * @return
     */
    @SuppressWarnings("unchecked")
    public T setArtifactTransferLogging(boolean artifactTransferLogging) {
        this.artifactTransferLogging = artifactTransferLogging;
        return (T) this;
    }

    /**
     * Resolves a POM file for a basedir.
     *
     * @param basedir project's basedir
     * @return POM file for the basedir or null, if it could not be resolved
     */
    public Path getPomForDirOrNull(Path basedir) {
        if (!Files.isDirectory(basedir)) {
            return null;
        }
        final String altPom = alternatePomName == null
                ? getInitializedCliOptions().getOptionValue(BootstrapMavenOptions.ALTERNATE_POM_FILE)
                : alternatePomName;
        return BootstrapMavenContext.getPomForDirOrNull(basedir, altPom == null ? null : Paths.get(altPom));
    }

    /**
     * Root project directory.
     *
     * @param rootProjectDir root project directory
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setRootProjectDir(Path rootProjectDir) {
        this.rootProjectDir = rootProjectDir;
        return (T) this;
    }

    /**
     * By default POM artifacts of modules with packaging other than {@code pom} are resolved from the workspace
     * only if the main artifact has been built locally, otherwise both the main artifact and the POM will be
     * resolved from a Maven repository (local and/or remote).
     * <p>
     * Enabling this option will make the resolver ignore the fact that the main artifact hasn't been built yet and
     * will pick up its {@code pom} from the workspace.
     *
     * @param preferPomsFromWorkspace whether the POM artifact should always be resolved from the workspace
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setPreferPomsFromWorkspace(boolean preferPomsFromWorkspace) {
        this.preferPomsFromWorkspace = preferPomsFromWorkspace;
        return (T) this;
    }

    /**
     * When workspace is loaded, the current implementation reads the POM files of every project found and
     * initializes the workspace model based on the raw POMs. This approach has its limitations, e.g.
     * it doesn't properly support interpolation of POMs, including properties and profiles. But it is
     * relatively fast compared to the resolving the effective POMs.
     * <p>
     * This option enables workspace initialization based on effective POMs of every found project.
     *
     * @param effectiveModelBuilder whether to enable effective model builder for workspace discovery
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setEffectiveModelBuilder(boolean effectiveModelBuilder) {
        this.effectiveModelBuilder = effectiveModelBuilder;
        return (T) this;
    }

    /**
     * Whether to enable initialization of the parent hierarchy for discovered
     * {@link io.quarkus.bootstrap.workspace.WorkspaceModule}s.
     * Enabling complete POM hierarchy is useful for project info and update use-cases but not really for runtime be it test or
     * dev modes.
     * <p>
     *
     * @param wsModuleParentHierarchy whether to initialize parents for {@link io.quarkus.bootstrap.workspace.WorkspaceModule}
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setWorkspaceModuleParentHierarchy(boolean wsModuleParentHierarchy) {
        this.wsModuleParentHierarchy = wsModuleParentHierarchy;
        return (T) this;
    }

    /**
     * When workspace discovery is enabled, this method allows to set a POM
     * provider that would return a {@link org.apache.maven.model.Model} for
     * a given workspace module directory.
     *
     * @param modelProvider POM provider
     * @return this instance
     */
    @SuppressWarnings("unchecked")
    public T setProjectModelProvider(Function<Path, Model> modelProvider) {
        this.modelProvider = modelProvider;
        return (T) this;
    }

    /**
     * Whether to warn about failures loading workspace modules instead of throwing errors
     *
     * @param warnOnFailedWorkspaceModules whether to warn about failures loading workspace modules instead of throwing errors
     * @return this config instance
     */
    @SuppressWarnings("unchecked")
    public T setWarnOnFailedWorkspaceModules(boolean warnOnFailedWorkspaceModules) {
        this.warnOnFailedWorkspaceModules = warnOnFailedWorkspaceModules;
        return (T) this;
    }

    private BootstrapMavenOptions getInitializedCliOptions() {
        return cliOptions == null ? cliOptions = BootstrapMavenOptions.newInstance() : cliOptions;
    }
}

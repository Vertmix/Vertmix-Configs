rootProject.name = "configs"

listOf(
    "api",
    "json",
    "toml",
    "yaml"
).forEach{ setupProject("config-${it.replace("/", "-")}", file(it)) }

fun setupProject(name: String, projectDirectory: File) = setupProject(name) {
    projectDir = projectDirectory
}

inline fun setupProject(name: String, block: ProjectDescriptor.() -> Unit) {
    include(name)
    project(":$name").apply(block)
}

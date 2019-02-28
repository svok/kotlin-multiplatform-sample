mapOf(
    Pair("node_plugin_version", "1.2.0"),
    Pair("docker_plugin_version", "1.2")
).entries.forEach {
    project.extra.set(it.key, it.value)
}

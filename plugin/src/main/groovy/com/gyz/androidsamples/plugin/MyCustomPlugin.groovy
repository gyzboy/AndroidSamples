package com.gyz.androidsamples.plugin;

import org.gradle.api.Plugin
import org.gradle.api.Project

class MyNestPluginExtension {
    def receiver = "guoyizhe"
    def email = "gyzboy@126.com"
}

class MyCustomPluginExtension {
    def message = "From MyCustomPluginExtention"
    def sender = "MyCustomPluin"
}

class MyCustomPlugin implements Plugin<Project> {
    void apply(Project project) {
        project.extensions.create('myArgs', MyCustomPluginExtension)
        project.myArgs.extensions.create('nestArgs', MyNestPluginExtension)
        project.task('customTask', type: MyCustomTask)
    }
}
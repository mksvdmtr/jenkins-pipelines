properties([
    parameters([
        string(
            name: 'DOCKER_TAG',
            defaultValue: '',
            description: 'Необходимо ввести DOCKER TAG, собранного релиза, который ранее устанавливался на DEV стенды'
        )
    ])
])

pipeline {
    agent any

        stages {
            stage('Hello') {
                steps {
                    script {
                        def causes = currentBuild.getBuildCauses()
                        echo "${causes}"
                        if (currentBuild.getBuildCauses('org.jenkinsci.plugins.workflow.support.steps.build.BuildUpstreamCause')) {
                        // if (currentBuild.getBuildCauses()[0].toString().contains('BuildUpstreamCause')) { 
                            echo "Hello World From JOB2 - DOCKER_TAG: ${params.DOCKER_TAG}"
                        } else {
                            echo "Triggered by something else"
                            wrap([$class: 'BuildUser']) {
                                def build_user = "${env.BUILD_USER}"
                            }
                       }
                    }
                }
            }
            stage('Hello2') {
                steps {
                    script {
                        echo "USER: ${build_user}"
                    }
                }
            }
        }
}
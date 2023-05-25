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
                        if (currentBuild.getBuildCauses()[0].toString().contains('BuildUpstreamCause')) { 
                            echo "Hello World From JOB2 - DOCKER_TAG: ${params.DOCKER_TAG}"
                        } else {
                            echo "Triggered by something else"

                        }
                }
            }
        }
    }
}
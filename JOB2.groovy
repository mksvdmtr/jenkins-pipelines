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
                        if triggeredBy(cause: 'UpstreamCause', detail: 'JOB1') {
                            echo "Hello World From JOB2 - DOCKER_TAG: ${params.DOCKER_TAG}"
                        } else {
                            echo "Triggered by something else"

                        }
                }
            }
        }
    }
}
properties([
    parameters([
        string(
            name: 'DOCKER_TAG',
            defaultValue: 'master_c9ecef82703__STABLE1111',
            description: 'Необходимо ввести DOCKER TAG, собранного релиза, который ранее устанавливался на DEV стенды'
        )
    ])
])

pipeline {
    agent any

    stages {
        stage('Hello') {
            steps {
                echo "Hello World From JOB2 - DOCKER_TAG: ${params.DOCKER_TAG}"
            }
        }
    }
}
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
                            wrap([$class: 'BuildUser']) {
                                build_user = "${env.BUILD_USER}"
                            }
                       }
                    }
                }
            }
            stage('Hello2') {
                steps {
                    script {
                        echo "USER: `${build_user}`"
                    }
                }
            }
        }
}
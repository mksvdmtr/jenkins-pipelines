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
            stage('env') {
                steps {
                    script {
                            wrap([$class: 'BuildUser']) {
                                build_user = "${env.BUILD_USER}"
                            }
                       }
                    }
                }
            stage('s1') {
                steps {
                    script {
                        echo "USER: `${build_user}`"
                    }
                }
            }
        }
}
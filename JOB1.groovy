CRON_DOCKER_TAG='master_0bc8a1d23b8__LTS'

properties([
    parameters([
        choice(
            name: 'STAND_NAME',
            description: '',
            choices: [
                'STAGING',
                'DC1'
            ]
        ),
        choice(
            name: 'ACTION',
            description: 'Действие с gitlab',
            choices: [
                'pipelines-cleaner',
                's3-cleaner'
            ]
        ),
        string(
            name: 'DOCKER_TAG',
            defaultValue: 'latest',
            description: 'Необходимо ввести DOCKER TAG, собранного релиза, который ранее устанавливался на DEV стенды'
        ),
        string(
            name: 'CONFIG_BRANCH',
            defaultValue: '-',
            description: 'Ветка с конфигурацией',
            trim: true
        ),
         booleanParam(
            name: 'CRON_DOCKER_TAG_APPLY_ONLY',
            defaultValue: false,
            description: 'Применить новый Docker tag'
        )
    ])
])

pipeline {
    agent any
    
    triggers {
        parameterizedCron("*/3 * * * * %STAND_NAME=DC1;ACTION=pipelines-cleaner;DOCKER_TAG=${CRON_DOCKER_TAG}")
    }

    stages {
        stage('Cron Docker tag apply'){
            when {
                expression { params.CRON_DOCKER_TAG_APPLY_ONLY }
            }
            steps {
                script {
                    if (currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause')) {
                        log.info('Cron Docker tag applied')
                    }
                }
            }
        }
        stage('Hello') {
            steps {
                script {
                    if (!(currentBuild.getBuildCauses()[0].toString().contains('ParameterizedTimerTriggerCause'))) {
                        timeout(time:5, unit:'MINUTES') {
                            input message: "Вы подтверждаете запуск"
                        }
                    }
                    HELMFILE_CMD = "TOOL_ACTION=${params.ACTION} FIXED_TAG=${params.DOCKER_TAG} helmfile --allow-no-matching-release "
                    echo "Hello World From JOB1 HELMFILE_CMD: ${HELMFILE_CMD} STAND: ${params.STAND_NAME}"
                    echo "${currentBuild.getBuildCauses()}"
                }
            }
        }
    }
    post {
        success {
            script {
                if (!(currentBuild.getBuildCauses('hudson.model.Cause$UserIdCause'))) {
                    build('JOB2')    
                }
            }
        }
    }
}


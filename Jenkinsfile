pipeline {
    agent any

    stages {
        stage ('Build Stage') {

            steps {
                withMaven(maven : 'maven_3_6_3') {
                    sh 'mvn clean install'
                }
            }
        }
        
        stage ('Compile Stage') {

            steps {
                withMaven(maven : 'maven_3_6_3') {
                    sh 'mvn compile'
                }
            }
        }

        stage ('Testing Stage') {

            steps {
                withMaven(maven : 'maven_3_6_3') {
                    sh 'mvn test'
                }
            }
        }
         stage ('Sonnar Stage') {

            steps {
                withMaven(maven : 'maven_3_6_3') {
                    sh 'mvn package sonar:sonar'
                }
            }
        }


        stage ('Deployment Stage') {
            steps {
                withMaven(maven : 'maven_3_6_3') {
                    sh 'mvn clean deploy'
                }
            }
        }
    }
}


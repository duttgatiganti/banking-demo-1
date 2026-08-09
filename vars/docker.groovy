def call(String imageName, String credentialId) {

    withCredentials([
        usernamePassword(
            credentialsId: credentialId,
            usernameVariable: 'DOCKER_USERNAME',
            passwordVariable: 'DOCKER_PASSWORD'
        )
    ]) {

        sh """
            set -e

            echo "Building image: ${imageName}:${env.BUILD_NUMBER}"

            docker build -t ${imageName}:${env.BUILD_NUMBER} .

            docker images

            echo "\$DOCKER_PASSWORD" | docker login \
                -u "\$DOCKER_USERNAME" \
                --password-stdin

            docker push ${imageName}:${env.BUILD_NUMBER}
        """
    }
}

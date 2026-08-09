def call(String imageName, String credentialId, String username) {

    withCredentials([
        string(
            credentialsId: credentialId,
            variable: 'DOCKER_PASSWORD'
        )
    ]) {

        sh """
            set -e

            docker build -t ${imageName}:${env.BUILD_NUMBER} .

            docker images

            echo "\$DOCKER_PASSWORD" | docker login \
                -u "${username}" \
                --password-stdin

            docker push ${imageName}:${env.BUILD_NUMBER}
        """
    }
}

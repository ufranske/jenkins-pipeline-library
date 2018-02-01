#!groovy

def call(String url, String branch, String credentialsId, String relativeTargetDir, String fallbackBranch = null, boolean shallow = true){
    def branches = fallbackBranch ? [branch, fallbackBranch] : [branch]
    def extensions = shallow ? [[$class: 'CloneOption', shallow: true, depth: 1, noTags: true]] : []

    dir(relativeTargetDir) {
        checkout(
            resolveScm(
                source: [$class: 'GitSCMSource',
                    remote: url,
                    credentialsId: credentialsId,
                    id: '_',
                    traits: [[$class: 'jenkins.plugins.git.traits.BranchDiscoveryTrait']],
                    extensions: extensions,
                    subModuleCfg: [],
                ],
                targets: branches
            )
        )
    }

}


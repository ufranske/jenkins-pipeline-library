#!groovy

def call(Map checkoutRules, String branch, String credentialsId, String relativeTargetDir = null, String fallbackBranch = null) {
    def resolveDir = relativeTargetDir ? { subDir -> "${relativeTargetDir}/${subDir}" } : { it }
    def checkoutWorkers = checkoutRules.collectEntries{ url, relPath -> [
        (url) : {
            gitCheckout(url, branch, credentialsId, resolveDir(relPath), fallbackBranch, true)
        }
    ]}
    parallel(checkoutWorkers)
}


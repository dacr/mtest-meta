def config = new HashMap()
def binding = getBinding()
config.putAll(binding.getVariables())

def basepath='mtest'
def repobase=config.get('REPO_BASEURL', 'https://github.com/dacr/')

folder("${basepath}") {
}

job("${basepath}/mtest-dep") {
  scm {
    git {
      remote {
        url("${repobase}/mtest-dep.git")
      }
      branch('master')
    }
  }
  triggers {
    scm('H/2 * * * *')
  }
  steps {
      maven('clean test install')
  }
}



job("${basepath}/mtest-web-project") {
  scm {
    git {
      remote {
        url("${repobase}/mtest-web-project.git")
      }
      branch('master')
    }
  }
  triggers {
      scm('H/2 * * * *')
      upstream("${basepath}/mtest-dep")
  }
  steps {
      maven('clean package install')
  }
}




job("${basepath}/mtest-deploy") {
  scm {
    git {
      remote {
        url("${repobase}/mtest-deploy.git")
      }
      branch('master')
    }
  }
  triggers {
    upstream("${basepath}/mtest-web-project")
  }
  steps {
      maven('dependency:copy')
  }
  publishers {
      publishScp('backend') {
          entry('mtest-web-project.war', '.', true)
      }
  }
}



job("${basepath}/mtest-loadtest") {
  scm {
    git {
      remote {
        url("${repobase}/mtest-loadtest.git")
      }
      branch('master')
    }
  }
  triggers {
    upstream("${basepath}/mtest-deploy")
  }
  steps {
      maven('gatling:execute')
  }
  publishers {
      archiveGatling()
  }
}



listView("${basepath}") {
  jobs {
    name("${basepath}")
    regex("${basepath}/mtest.*")
  }
  columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
  }
}

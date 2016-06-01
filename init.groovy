def basepath='mtest'
def repobase='https://github.com/dacr/'
//def repobase='http://10.236.246.220:9090/crodav'

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
      // TODO - deploy to the integration server
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
}



listView('mtest') {
  jobs {
    name("${basepath}")
  }
  columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
  }
}

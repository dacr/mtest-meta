def basepath='mtest'

folder("${basepath}") {
}

job("${basepath}/mtest-dep") {
  scm {
    git {
      remote {
        url("http://10.236.246.220:9090/crodav/mtest-dep.git")
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
        url("http://10.236.246.220:9090/crodav/mtest-web-project.git")
      }
      branch('master')
    }
  }
  triggers {
      scm('H/2 * * * *')
  }
  steps {
      maven('clean package')
  }
}


listView('mtest') {
  jobs {
    name(${basepath}")
  }
  columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
  }
}

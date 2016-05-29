job("mtest-dep") {
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



job("mtest-web-project") {
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
    name('mtest-dep')
    name('mtest-web-project')
  }
  columns {
      status()
      weather()
      name()
      lastSuccess()
      lastFailure()
  }
}

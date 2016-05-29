

job("mtest-dep") {
  scm {
    git("http://10.236.246.220:9090/crodav/mtest-dep.git")
  }
  triggers {
    scm('*/2 * * * *')
  }
  steps {
      maven('clean test install')
  }
}



job("mtest-web-project") {
  scm {
    git("http://10.236.246.220:9090/crodav/mtest-web-project.git")
  }
  triggers {
      scm('*/2 * * * *')
  }
  steps {
      maven('clean package')
  }
}

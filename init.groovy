job("mtest-dep") {
  scm {
      git {
          remote {
            git("http://10.236.246.220:9090/crodav/mtest-dep.git")
          }
          createTag(false)
      }
  }
  triggers {
      scm('*/2 * * * *')
  }
  steps {
      maven('-e clean test install')
  }
}

job("mtest-web-project") {
  scm {
      git {
          remote {
            git("http://10.236.246.220:9090/crodav/mtest-web-project.git")
          }
          createTag(false)
      }
  }
  triggers {
      scm('*/2 * * * *')
  }
  steps {
      maven('-e clean package')
  }
}

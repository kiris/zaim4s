# zaim4s
This is a `Zaim API` binding library for the Scala language.

https://dev.zaim.net

supported scala version is `2.12.x`

## Setup
Edit file `project/Build.scala` or `build.sbt`
```scala
resolvers += "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases/"

libraryDependencies += "com.github.kiris" %% "zaim4s" % "0.1"
```

## Usage

```scala
import com.github.kiris.zaim4s.Zaim
import org.asynchttpclient.oauth.{ConsumerKey, RequestToken}

import scala.concurrent.ExecutionContext.Implicits.global

val zaim = Zaim(
  new ConsumerKey(
    "<your consumer key>",
    "<your consumer secret>"
  ),
  new RequestToken(
    "<your access token key>",
    "<your access token secret>"
  )
)

// Showing the list of input data.
zaim.getMoneys(limit = 5)

// Showing the list of input data group by receipt id.
zaim.getMoneysGroupByReceiptId(limit = 5)
```

## License

Apache License, Version 2.0

Copyright 2017 Yoshiaki Iwanaga [@kiris](https://twitter.com/kiris)

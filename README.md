# Config Example
A sample project showing how to load a configuration using [TypeSafe Config](https://github.com/lightbend/config)
and [PureConfig](https://pureconfig.github.io/).

## TypeSafe Config
[TypeSafe Config](https://github.com/lightbend/config) is a well known and often used library to read configuration files for an application. The disadvantage
of this library is that you need to read the individual configuration parameters manually and you can get an error
when you read a configuration parameter. If you don't read all configuration parameters at the start of your program,
you could get an exception when reading a configuration parameter during running your program.

See the `TypeSafeConfig` program for the usage.

## PureConfig
[PureConfig](https://pureconfig.github.io/) reads TypeSafe Config's configuration files, so both libraries use the same configuration file format, only
PureConfig makes it easier to read your configuration and to detect errors in your configuration: you define case classes
matching your configuration and use `pureconfig.loadConfig[Config]` where `Config` is your configuration case class to read
the configuration.

Reading the configuration in this way when starting your program will get an `Either` of configuration failures
or your `Config` case class. Reading the configuration in this way you can ensure that the configuration is as expected before starting the rest
of your program.

See the `PureConfig` program for the usage.

## Configuration for different environments
You typically have different configurations for different environments. E.g. the host for your database and username and
password for it usually differ for a development and a production environment. You can customize a configuration for a 
different environment in two different ways:

- Use a different configuration file per environment to override default values.
- Use environment variables to override default values.

This sample project shows both ways in one project, but you can pick one or also use both methods at the same time.

### Using a different configuration file per environment
Use a `reference.conf` file in your project and create an `application.conf` (or named differently) for your different environments.
The first line of your configuration should be `include "reference.conf"`. This loads the default values from the `reference.conf` file
so you only need to override values when the default values aren't appropriate. The `application.conf` file in `configfiles/application.conf`
overrides the database password as an example.

To use the configuration file, set the `config.file` system property to the path of your configuration file when you
run the application, e.g.:
 
`sbt -Dconfig.file=configfiles/application.conf run`

### Local development
For local development you can create a file with your overrides and use the `config.file` property to use it.
If everybody working on the project uses the same name for this file (e.g. `development.conf`), you can add it to `.gitignore` to prevent that
it accidentally ends up in git.

## Use environment variables to override default values
Use again a `reference.conf` file in your project and include for the settings that possibly need be overridden an
environment variable, which if present, will override the default setting, e.g.:

```
password = "pw"
password = ${?DATABASE_PASSWORD}
```

The question mark before `DATABASE_PASSWORD` makes the presence of the environment variable optional.
So if the environment variable `DATABASE_PASSWORD` is present, it will be used, otherwise the default value `pw` will be used.

Before starting the program, make sure the environment variables have been defined, e.g. (for Linux):

```
DATABASE_PASSWORD=secret
export DATABASE_PASSWORD
sbt run
```

Omitting the question mark is generally not a good idea because this forces someone who wants to run the application locally
to set environment variables. It is better to always have sensible defaults.

### Local development
For local development you can use the sbt plugin [sbt-dotenv](https://github.com/Philippus/sbt-dotenv). This allows you
to create a `.env` file in which you set your environment variables. Adding this file to `.gitignore` ensures that is doesn't
accidentally ends up in git. It is a good practise to provide a `.env.sample` file with the environment variables and the default
settings so every developer can use this to create his or her `.env` file.

You also need to create a `.jvmopts` file in your project with the following content to
avoid errors with using [sbt-dotenv](https://github.com/Philippus/sbt-dotenv):

```
--add-opens=java.base/java.util=ALL-UNNAMED
--add-opens=java.base/java.lang=ALL-UNNAMED
```

## Combining both methods
Sometimes when you use a different configuration file per environment you also may want to use environment variables
because you don't want passwords to be available in plain text in a configuration file, but read these from a vault and
set these through environment variables.

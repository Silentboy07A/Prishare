# Upgrade Plan: backend (20260614081654)

- **Generated**: 2026-06-14 08:16:54
- **HEAD Branch**: main
- **HEAD Commit ID**: unknown

## Available Tools

**JDKs**
- JDK 21.0.6: C:\Program Files\Java\jdk-21\bin (required by steps 1, 3, 4)

**Build Tools**
- Maven 3.9.9: C:\Users\csbal\Downloads\apache-maven-3.9.9-bin (1)\apache-maven-3.9.9\bin
- Maven Wrapper: 3.9.16 (current, compatible with Java 21)

## Guidelines

- Upgrade the Java runtime to the latest LTS version while preserving existing Spring Boot 3.5.3 compatibility.
- Minimize changes to the build configuration and application code.

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260614081654
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java: 17 → 21

## Technology Stack

| Technology/Dependency    | Current | Min Compatible | Why Incompatible |
| ------------------------ | ------- | -------------- | ---------------------------------------------- |
| Java                     | 17      | 21             | User requested latest LTS runtime              |
| Spring Boot              | 3.5.3   | 3.5.3          | Already compatible with Java 21                |
| Maven Wrapper            | 3.9.16  | 3.9.0+         | Compatible with Java 21                        |
| Maven                    | 3.9.9   | 3.9.0+         | Compatible with Java 21                        |
| maven-compiler-plugin    | managed by Spring Boot parent | 3.11.0+ recommended | Latest JDK compile support is handled by the parent BOM |

## Derived Upgrades

- Upgrade the Maven `java.version` property from 17 to 21 in `pom.xml`.
  - Reason: Target runtime is Java 21 and Spring Boot 3.5.x supports Java 21.
- No Spring Boot version upgrade is required because the current version is already compatible with Java 21.
- No build tool wrapper upgrade is required because Maven wrapper 3.9.16 is already compatible.

## Impact Analysis

### Dependency Changes

| File | Dependency | Current | Action | Target | Reason |
|------|------------|---------|--------|--------|--------|
| pom.xml | `<java.version>` | 17 | upgrade | 21 | Target Java 21 runtime |

### Source Code Changes

| File | Location | Current | Required Change | Reason |
|------|----------|---------|----------------|--------|
| None | - | - | - | Java 21 upgrade does not require code changes in the current codebase |

### Configuration Changes

| File | Property/Setting | Current | Required Change | Reason |
|------|------------------|---------|----------------|--------|
| None identified | - | - | - | No application configuration changes required for Java 21 upgrade |

### CI/CD Changes

| File | Location | Current | Required Change |
|------|----------|---------|----------------|
| None identified | - | - | - |

### Risks & Warnings

- **Baseline skip risk**: Current project JDK 17 is not installed on this machine, so baseline validation with the original runtime will be skipped. Verification will proceed with Java 21.
- **Spring Boot compatibility**: Spring Boot 3.5.3 is expected to support Java 21, but the build must still be verified after updating `java.version`.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Ensure Java 21 and Maven are available before making build changes.
  - **Changes to Make**: Confirm available JDK and Maven installation paths.
  - **Verification**: `mvn -version` using Maven wrapper or installed Maven, JDK 21 path available.

- Step 2: Setup Baseline
  - **Rationale**: Attempt baseline validation with the original runtime if available.
  - **Changes to Make**: None if base JDK is missing.
  - **Verification**: skipped because Java 17 is not installed.

- Step 3: Upgrade Java version to 21
  - **Rationale**: Apply the target runtime change in the Maven build and preserve existing Spring Boot compatibility.
  - **Changes to Make**: Update `pom.xml` `<java.version>` from 17 to 21.
  - **Verification**: `./mvnw clean test-compile -q` or `mvnw.cmd clean test-compile -q` with JDK 21.

- Step 4: Final Validation
  - **Rationale**: Confirm the project compiles and all tests pass under Java 21.
  - **Changes to Make**: None beyond the Java version update.
  - **Verification**: `./mvnw clean test` or `mvnw.cmd clean test` with JDK 21.

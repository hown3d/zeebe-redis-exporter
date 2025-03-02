package io.zeebe.redis.exporter;

import java.time.Duration;
import java.util.Optional;

public class ExporterConfiguration {

  private static final String ENV_PREFIX = "ZEEBE_REDIS_";

  private String format = "protobuf";

  private String enabledValueTypes = "";
  private String enabledRecordTypes = "";

  private String name = "zeebe";

  private String remoteAddress;

  private long cleanupCycleInSeconds = Duration.ofMinutes(1).toSeconds();

  private long minTimeToLiveInSeconds = 0;

  private long maxTimeToLiveInSeconds = Duration.ofMinutes(5).toSeconds();

  private boolean deleteAfterAcknowledge = false;

  public long getCleanupCycleInSeconds() {
    return getEnv("CLEANUP_CYCLE_IN_SECONDS").map(Long::parseLong).orElse(cleanupCycleInSeconds);
  }

  public long getMinTimeToLiveInSeconds() {
    return getEnv("MIN_TIME_TO_LIVE_IN_SECONDS").map(Long::parseLong).orElse(minTimeToLiveInSeconds);
  }

  public long getMaxTimeToLiveInSeconds() {
    return getEnv("MAX_TIME_TO_LIVE_IN_SECONDS").map(Long::parseLong).orElse(maxTimeToLiveInSeconds);
  }

  public boolean isDeleteAfterAcknowledge() {
    return getEnv("DELETE_AFTER_ACKNOWLEDGE").map(Boolean::parseBoolean).orElse(deleteAfterAcknowledge);
  }

  public String getFormat() {
    return getEnv("FORMAT").orElse(format);
  }

  public String getEnabledValueTypes() {
    return getEnv("ENABLED_VALUE_TYPES").orElse(enabledValueTypes);
  }

  public String getEnabledRecordTypes() {
    return getEnv("ENABLED_RECORD_TYPES").orElse(enabledRecordTypes);
  }

  public String getName() {
    return getEnv("NAME").orElse(name);
  }

  public Optional<String> getRemoteAddress() {
    return getEnv("REMOTE_ADDRESS")
            .or(() -> Optional.ofNullable(remoteAddress))
            .filter(remoteAddress -> !remoteAddress.isEmpty());
  }

  private Optional<String> getEnv(String name) {
    return Optional.ofNullable(System.getenv(ENV_PREFIX + name));
  }

  @Override
  public String toString() {
    return "[" +
            "remoteAddress='" + getRemoteAddress() + '\'' +
            ", enabledValueTypes='" + getEnabledValueTypes() + '\'' +
            ", enabledRecordTypes='" + getEnabledRecordTypes() + '\'' +
            ", format='" + getFormat() + '\'' +
            ", name='" + getName() + '\'' +
            ", cleanupCycleInSeconds=" + getCleanupCycleInSeconds() +
            ", minTimeToLiveInSeconds=" + getMinTimeToLiveInSeconds() +
            ", maxTimeToLiveInSeconds=" + getMaxTimeToLiveInSeconds() +
            ", deleteAfterAcknowledge=" + isDeleteAfterAcknowledge() +
            ']';
  }
}

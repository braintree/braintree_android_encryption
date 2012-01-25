def do_cmd(cmd)
    return_code = Kernel.system cmd
    raise "'#{cmd}' failed" unless return_code
end

task :default => [:check_version, :test]

desc "run unit tests"
task :test do
  return_code = 0
  Dir.chdir("test") do
    do_cmd 'ant clean debug install test'
  end
end

desc "check if versions match for Braintree.java and AndroidManifest.xml"
task :check_version do
  if version != manifest_version
    raise "Braintree.java version does not match AndroidManifest.xml version"
  end
end

desc "update versions on both Braintree.java and AndroidManifest.xml"
task :update_version do
  raise "ENV['ANDROID_VERSION'] is not defined" if ENV["ANDROID_VERSION"].nil?
  update_versions
end

desc "ant clean"
task :clean do
  return_code = 0
  do_cmd 'ant clean'
  Dir.chdir("test") do
    do_cmd 'ant clean'
  end
end

def braintree_java_file
  'src/com/braintreegateway/encryption/Braintree.java'
end

def android_manifest
  'AndroidManifest.xml'
end

def update_versions
  contents = File.read(braintree_java_file)
  contents.gsub!(/VERSION = ".*"/, "VERSION = \"#{ENV["ANDROID_VERSION"]}\"")
  File.open(braintree_java_file, 'w') { |f| f.write(contents); f.close }

  manifest_contents = File.read(android_manifest)
  manifest_contents.gsub!(/android:versionName=".*"/, "android:versionName=\"#{ENV["ANDROID_VERSION"]}\"")
  File.open(android_manifest, 'w') { |f| f.write(manifest_contents); f.close }
end

def version
  contents = File.read(braintree_java_file)
  version = contents.slice(/VERSION = "(.*)"/, 1)
  raise "Cannot read version" if version.empty?
  version
end

def manifest_version
  contents = File.read(android_manifest)
  version = contents.slice(/android:versionName="(.*)"/, 1)
  raise "Cannot read version" if version.empty?
  version
end

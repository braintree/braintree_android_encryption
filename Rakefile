require 'fileutils'

task :default => [:test]

desc "run unit tests"
task :test do
  output = `adb devices`
  if output.match(/device$/)
    sh "./gradlew --info clean connectedAndroidTest"
  else
    puts "Please connect a device or start an emulator and try again"
    exit 1
  end
end

task :release => :test do
  last_version = `git tag | tail -1`.chomp
  puts "Changes since #{last_version}:"
  sh "git log --pretty=format:\"%h %ad%x20%s%x20%x28%an%x29\" --date=short #{last_version}.."
  puts
  puts "Please update your CHANGELOG.md. Press ENTER when you are done"
  $stdin.gets

  puts "What version are you releasing? (x.x.x format)"
  version = $stdin.gets.chomp

  increment_version_code
  update_version(version)

  sh "./gradlew clean uploadArchives"
  puts "BraintreeAndroidEncryption ${version} was uploaded, please promote it on oss.sonatype.org. Press ENTER when you have promoted it"
  $stdin.gets

  sh "git commit -am 'Release #{version}'"
  sh "git tag #{version} -am '#{version}'"

  puts "Commits and tags have been created. If everything appears to be in order, hit ENTER to push."
  $stdin.gets

  sh "git push origin master"
  sh "git push --tags"
  sh "git push github master"
  sh "git push github --tags"
end

def build_gradle_file
  'BraintreeAndroidEncryption/build.gradle'
end

def increment_version_code
  new_build_file = ""
  File.foreach(build_gradle_file) do |line|
    if line.match(/versionCode (\d+)/)
      new_build_file += line.gsub(/versionCode \d+/, "versionCode #{$1.to_i + 1}")
    else
      new_build_file += line
    end
  end
  IO.write(build_gradle_file, new_build_file)
end

def update_version(version)
  IO.write(build_gradle_file,
    File.open(build_gradle_file) do |file|
      file.read.gsub(/versionName '\d+\.\d+\.\d+'/, "versionName '#{version}'")
    end
  )
end

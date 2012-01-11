def do_cmd(cmd)
    return_code = Kernel.system cmd
    raise "'#{cmd}' failed" unless return_code
end

task :default do
  return_code = 0
  Dir.chdir("test") do
    do_cmd 'ant clean debug install test'
  end
end

task :clean do
  return_code = 0
  do_cmd 'ant clean'
  Dir.chdir("test") do
    do_cmd 'ant clean'
  end
end

def do_cmd(cmd)
    return_code = Kernel.system cmd
    raise "'#{cmd}' failed" unless return_code
end
task :default do
  return_code = 0
  do_cmd 'ant debug'
  Dir.chdir("test") do
    do_cmd 'ant debug install' rescue
    do_cmd 'ant test'
  end
  do_cmd 'ant release'
end


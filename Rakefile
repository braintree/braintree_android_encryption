task :default do
  return_code = 0
  Dir.chdir("test") do
    return_code = exec 'ant debug'
    raise "Debug failed" unless return_code == 0
    return_code = exec 'ant test'
    raise "Test failed" unless return_code == 0
  end
  return_code = exec 'ant release'
  return_code
end


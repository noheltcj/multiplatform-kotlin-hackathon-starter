Pod::Spec.new do |s|
  s.name                    = 'StarterCore'
  s.version                 = '0.0.1'
  s.summary                 = '${summary}'
  s.author                  = { 'Colton Nohelty' => 'noheltycolton@gmail.com' }
  s.homepage                = 'https://github.com/noheltcj'
  s.license                 = { :type => 'MIT', :file => 'LICENSE' }
  s.platform                = :ios
  s.requires_arc            = true
  s.module_name             = "ST"
  s.source                  = { :http => 'https://s3.us-east-2.amazonaws.com/starter-archive/0.0.1/StarterCore.zip' }
  s.source_files            = 'build/native/release/StarterCore.framework/Headers/*.h'
  s.resource                = 'build/native/release/StarterCore.framework/Info.plist'
  s.public_header_files     = 'build/native/release/StarterCore.framework/Headers/*.h'
  s.ios.deployment_target   = '11.0'
  s.ios.vendored_frameworks = 'build/native/release/StarterCore.framework'
end

function dosomething()
  mvp()
  for i=1,#enemies do
    if ecoll(p,enemies[i]) then
      enemies[i].dead=true
    end
    turne(enemies[i])
    mve(enemies[i])
  end
  collect()
end
